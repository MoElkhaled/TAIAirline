import sys
import requests

API_URL = "http://localhost:8080/flight-paths?origin=Dallas&destination=Houston&class=ECONOMY"

def main():
    print(f"Calling API: {API_URL}")

    try:
        response = requests.get(API_URL, timeout=5)
    except Exception as e:
        print(f"❌ Failed to call API: {e}")
        sys.exit(1)

    if response.status_code != 200:
        print(f"❌ Expected status 200, got {response.status_code}")
        sys.exit(1)

    print("✅ API responded with HTTP 200")
    sys.exit(0)

if __name__ == "__main__":
    main()
