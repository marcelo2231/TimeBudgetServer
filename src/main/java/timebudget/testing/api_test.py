import requests
import json

# call fake_it
resp_body = requests.get("http://localhost:8080/fake_it").text
user = json.loads(resp_body)
assert sorted(user.keys()) == ['createdAt', 'email', 'password', 'token', 'userID', 'username']

# log in
body = '{"username": "test_user5420", "password": "password"}'
resp = requests.post("http://localhost:8080/user/login", data=body)
creds = json.loads(resp.text)
assert sorted(creds.keys()) == ["username","email","token"]

token = creds['token']

# get categoreis
credentials = requests.get("http://localhost:8080/log_in",params={'Authentication': user['token']}).text)
json.loads(