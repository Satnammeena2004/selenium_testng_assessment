# Selenium Assessment Framework

## Project Overview

Automated test suite for tutorialsninja.com/demo covering:

- Login flow (valid, invalid)
- Product search (valid term, invalid term, heading)
- Add to cart (success message, product name)

## Tech Stack

| Tool          | Purpose                    |
|---------------|----------------------------|
| Selenium 4    | Browser automation         |
| TestNG 7      | Test runner + annotations  |
| ExtentReports | HTML test reporting        |
| Maven         | Build + dependency manager |
| Git/GitHub    | Version control            |

## Folder Structure

```
SeleniumAssessment/
├── src/main/java/com/qa/
│   ├── base/BaseTest.java          → browser setup/teardown
│   ├── pages/                      → page object classes
│   └── utils/                      → helpers (wait, screenshot, report)
└── src/test/java/com/qa/
    ├── listeners/TestListener.java → TestNG event hooks
    └── tests/                      → test classes
```

## How to Run Locally

```bash
# Run all smoke tests on Chrome
mvn test -Dgroups=smoke -Dbrowser=chrome

# Run all regression tests on Firefox  
mvn test -Dgroups=regression -Dbrowser=firefox

# Run specific test class
mvn test -Dtest=LoginTest -Dbrowser=chrome
```

## Test Groups

| Group      | Tests                    | Count |
|------------|--------------------------|-------|
| smoke      | Critical path tests only | 4     |
| regression | Full suite               | 5     |
| login      | All login tests          | 3     |
| search     | All search tests         | 3     |
| cart       | All cart tests           | 2     |
| login      | All login tests          | 2     |

## Reports

After running, open: `reports/TestReport.html`

## Summary

- Successfully designed a Page Object Model-based Selenium framework
- Implemented TestNG grouping for flexible execution
- Integrated ExtentReports for detailed HTML reporting
- Achieved modular, reusable, and scalable test structure
- Covered critical flows:
- Login
- Search
- Add to Cart
- Framework supports cross-browser execution via parameters

## Author

STNAM