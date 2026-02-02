import sys
import requests

API_URL = "http://localhost:8080/flight-paths?origin=Dallas&destination=Houston&class=ECONOMY"
EXPECTED_RESULT1 = "[Dallas --> Tyler --> Ho: 294 miles: $294.0]"
TESTFAILED = False

def main():
    print(f"Calling API: {API_URL}")

    try:
        response = requests.get(API_URL, timeout=5)
    except Exception as e:
        print(f" Failed to call API: {e}")
        sys.exit(1)

    if response.status_code != 200:
        print(f"Expected status 200, got {response.status_code}")
        sys.exit(1)



    if response.text.strip() != EXPECTED_RESULT1:
        print("actual result did not match expected result")
        print(f"actual result: {response.text.strip()}")
        print(f"expected result: {EXPECTED_RESULT1}")
        sys.exit(1)
    else:
        print(f"Expected result for test 1: {EXPECTED_RESULT1}")
        print(f"Actual result for test 1: {response.text.strip()}")
        print("Test Passed!")
        sys.exit(0)

if __name__ == "__main__":
    main()
