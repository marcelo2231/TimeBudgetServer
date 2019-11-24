from __future__ import print_function
import random
import requests
import json

"""
For a larger API we should have written swagger documentation and had API tests
    automatically generated.
"""

def verify_category(category):
    correct_keys = ['categoryID', 'color', 'deletedAt', 'description', 'userID']
    for truth in correct_keys:
        if truth not in category:
            print(truth, "not in", category.keys())
            assert truth in category, truth + " not in " + str(category)
    assert len(category.keys()) == len(correct_keys), str(category.keys()) + " != " + str(correct_keys)

def verify_event(event, include_category=True):
    keys = ['categoryID', 'description', 'endAt', 'eventID', 'startAt']
    if not include_category:
        del keys[0]

    assert len(keys) == len(event.keys()), str(event)
    for truth in keys:
        if truth not in event:
            print(truth, "not in", event.keys())
            assert truth in event

# call fake_it
resp_body = requests.get("http://localhost:8080/fake_it").text
user = json.loads(resp_body)
assert sorted(user.keys()) == ['createdAt', 'email', 'password', 'token', 'userID', 'username'], str(sorted(user.keys()))
print("fake_it passed")

# log in
resp = requests.post("http://localhost:8080/user/login",
                     json={"username": user['username'], 'password': user['password']})
creds = json.loads(resp.text)
assert sorted(creds.keys()) == ["email","token","username"], str(sorted(creds.keys()))
print("login passed")

token = creds['token']

# categoreis/get_all_active
response = requests.get("http://localhost:8080/categories/get_all_active",
                        headers={'Authentication': token})
categories_object = json.loads(response.text)
assert list(categories_object.keys()) == ["categories"]
categories = categories_object["categories"]
assert len(categories) > 0
for cat in categories:
    verify_category(cat)

amusement_id = [cat for cat in categories if cat["description"] == "Amusement"][0]['categoryID']
print("get_active_categories passed")

# get events
body = json.dumps({"startAt": "0",
                   "endAt": str(3600 * 1000),
                   "categoryID": str(amusement_id)})

response = requests.get("http://localhost:8080/event/get_list",
                        headers={'Authentication': token},
                        data=body)
events_object = json.loads(response.text)
assert list(events_object.keys()) == ["events"]
events = events_object["events"]
assert len(events) > 0

for event in events:
    verify_event(event, include_category=False)

print("event/get_list passed")

# events/get_by_id
body = json.dumps({"eventID": events[0]['eventID']})

response = requests.get("http://localhost:8080/event/get_by_id",
                        headers={'Authentication': token},
                        data=body)

event = json.loads(response.text)
verify_event(event, include_category=True)

print("event/get_by_id passed")

# events/delete
body = json.dumps({"eventID": events[0]['eventID']})

response = requests.get("http://localhost:8080/event/delete",
                        headers={'Authentication': token},
                        data=body)
assert response.text == "true"
print("event/delete passed")

# events/edit
event1 = events[0]
event1['eventID'] = events[1]['eventID']
event1['startAt'] += 1
event1['endAt'] += 1
event1['categoryID'] = [cat['categoryID'] for cat in categories if cat['categoryID'] != amusement_id][0]
event1['description'] = 'new description'

response = requests.get("http://localhost:8080/event/edit",
                        headers={'Authentication': token},
                        json=event1)

body = json.dumps({"eventID": event1['eventID']})
response = requests.get("http://localhost:8080/event/get_by_id",
                        headers={'Authentication': token},
                        json=event1)
event = json.loads(response.text)
for key in ['categoryID', 'description', 'endAt', 'eventID', 'startAt']:
    assert event[key] == event1[key]

print("event/edit passed")

# event/create
del event1['eventID']
event1['startAt'] += 1
event1['endAt'] += 1
event1['categoryID'] = [cat['categoryID'] for cat in categories if cat['categoryID'] != amusement_id][0]
event1['description'] = 'another new description'

response = requests.get("http://localhost:8080/event/create",
                        headers={'Authentication': token},
                        json=event1)

event = json.loads(response.text)
for key in ['categoryID', 'description', 'endAt', 'startAt']:
    assert event[key] == event1[key]

print("event/create passed")


# categories/get_by_id
body = dict(categoryID=categories[0]['categoryID'])

response = requests.get("http://localhost:8080/categories/get_by_id",
                        headers={'Authentication': token},
                        json=body)
category = json.loads(response.text)
verify_category(category)

for key in ['categoryID', 'deletedAt', 'description', 'userID']:
    assert category[key] == categories[0][key]

print("categories/get_by_id passed")

# user/register
body = {"username":"username" + str(random.randint(0, 10000000)),
	    "password":"password",
	    "email":"email@mail.com"}
response = requests.get("http://localhost:8080/user/register",
                        headers={'Authentication': token},
                        json=body)
user = json.loads(response.text)

correct_keys = ['username', 'email', 'token']
for key in correct_keys:
    assert key in user and key != ""

assert len(user) == len(correct_keys)

print("user/register passed")

# categories/create
new_category = dict(description='new category', color=10000)

response = requests.get("http://localhost:8080/categories/create",
                        headers={'Authentication': token},
                        json=new_category)
category = json.loads(response.text)
verify_category(category)
assert category['description'] == new_category['description']
assert category['color'] == new_category['color']

print("categories/create passed")

# categories/update test change description and color
response = requests.get("http://localhost:8080/categories/get_all_active",
                        headers={'Authentication': token})
active_categories = json.loads(response.text)['categories']

category_to_update = active_categories[0]
category_to_update['color'] = 7987
category_to_update['description'] = 'new description'
category_to_update['deletedAt'] = 0

response = requests.get("http://localhost:8080/categories/update",
                        headers={'Authentication': token},
                        json=category_to_update)

assert json.loads(response.text)['status'] == 'success'

matching_categories = [cat for cat in json.loads(requests.get("http://localhost:8080/categories/get_all_active",
                        headers={'Authentication': token}).text)['categories'] if cat['categoryID'] == category_to_update['categoryID']]
assert len(matching_categories) == 1, "Category was wrongfully deactivate"
response_category = matching_categories[0]

verify_category(response_category)
assert category_to_update['description'] == response_category['description'], str(category_to_update) + " " + str(response_category)
assert category_to_update['color'] == response_category['color'], str(category_to_update) + " " + str(response_category)
assert response_category['deletedAt'] <= 0, "Error in get_all_active, an inactive category was returned."

print("categories/update passed")

# categories/update test reactivating a category


# categories/update test deactivating a category