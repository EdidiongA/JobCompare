# Test Plan

**Author**: Team 121

**Version History**:

- Version 1 - Initial release.
- Version 2 - Updated Test Plan for Deliverable 3 to distinguish between alpha/beta test goals and final test goals, revise the system test cases, and populate the test case table with preliminary test results.
- Version 3 - Updated Test Plan for Deliverable 4 to modify the unit test strategy and remove additional goals such as code coverage badges in GitHub.

## 1 Testing Strategy

### 1.1 Overall strategy

The application, herein referred to as the app, will undergo **unit testing**, **system testing**, and **regression testing**. Due to the nature of the app, which encompasses job comparisons based on a limited set of parameters, the number of discrete software components within the app will be relatively small with tight coupling. As such, **integration testing** is considered redundant and will not be performed in favor of unit testing and system testing.

**System testing** will be used as **validation criteria** to ensure that the app meets the customer requirements as specified in Assignment 5, herein referred to as the **Software Requirement Specification (SRS)**. Each user interface element, such as input fields and buttons, will be manipulated to produce the expected output. As a goal, system tests will be written by the **Tester**. System tests will ensure that all customer requirements have been satisfied and that the app is ready to be accepted and baselined as a release candidate. 

**Unit testing** will be used as **verification criteria** to ensure that implementation code performs according to the expectations of the developer. The job score formula within the app will be be the primary target of unit testing. As a goal, the unit test cases will be written by the **cognizant developer** who implemented the job score formula and will ensure that jobs are ranked correctly within the app. 

**Regression testing** refers to any system tests performed after modifications are made to a baselined version of the app and will be used as **continuous validation criteria**. Regression tests will be vital to ensure that changes made to the app do not cause it to fail any functional requirements. Regression test failures will herein be referred to as regressions.

### 1.2 Test Selection

The **SRS** contains a comprehensive list of requirements for the app. Requirements 1 through 6 are **functional requirements**. They are testable and will be used as the foundation for the system test cases, to be validated by testing. Requirements 7 through 9 are **non-functional requirements**. They are not directly testable and will be primarily validated by demonstration and inspection. They will be separately captured in the **ExtraRequirements.md** document.

**System test cases** will follow the **black-box technique**, whereby the output of the system will be measured by manipulating input parameters to produce the desired output, without regard for the underlying logic. All test cases provided in this document are system tests; that is, they will test the functional requirements of the system in accordance with the **SRS**. 

**Unit tests cases** will follow the **white-box technique**, whereby all accessible branches of the given code will be tested. All unit test cases will be determined at the time of implementation by the cognizant developer. Since unit tests are heavily reliant on the underlying implementation code, it is important to note that they are subject to change if the implementation changes. This is by design; a properly written unit test should fail if the tested code is substantially modified.

### 1.3 Adequacy Criterion

**System test adequacy** will be measured using the number of tested functional requirements as a metric, with a goal of satisfying 100% of all functional requirements. 

**Unit test adequacy** for the app will be measured by qualitative inspection. Having a correct job score formula is the primary goal of the unit tests, so adequacy will be established by implementing a minimum set of unit tests that demonstrate a correct formula.

**Test quality** will be controlled by a holding team peer review on Slack and addressing any critical feedback prior to submitting the app to the customer.

### 1.4 Bug Tracking

**Bug tracking** is an essential form of maintenance and will be a continuous process throughout the app's software development life cycle. Bugs represent cases where software defects or requirement non-conformances have escaped testing, whereas enhancement requests represent new features that may be nice to have or otherwise superfluous.

When a **bug** is identified, cognizant developers should report them on the Team 121 Slack channel and disposition accordingly. Cognizant users should submit an Issue in GitHub and label it as a `Bug Report`. The bug report should contain a description of the bug, expected behavior, actual behavior, steps to reproduce the behavior, and screenshots if available. Complete bug reports in GitHub will be evaluated by cognizant developers, categorized based on severity, and dispositioned accordingly. 

When an **enhancement** is identified, cognizant developers should report them on the Team 121 Slack channel and disposition accordingly. Cognizant users should submit an Issue in GitHub and label it as a `Feature Request`. Feature requests in GitHub will be evaluated by cognizant developers, categorized based on priority, and dispositioned accordingly.

### 1.5 Technology

**TSL Generator** will be used to determine the minimum number of system tests cases required to achieve full functional requirement coverage. All tests will be developed using the **JUnit** framework. User interface elements will be automatically activated using **Espresso**.

## 2 Test Cases

The **SRS** was analyzed to enumerate the total number of categories available in the app, which was determined to be 15. The rationale for 15 categories is straightforward; there are a total of 9 job details for any given job, 5 comparison settings, and 1 quantity of jobs selected to compare, yielding a total of 15. These categories represent the only parameters that a user may modify within the app, so testing them is sensible.

The 15 categories were determined to be:
1. Job title
2. Job company
3. Job location
4. Job cost of living
5. Job commute time
6. Job yearly salary
7. Job yearly bonus
8. Job retirement benefits
9. Job leave time
10. Commute time integer weight
11. Yearly salary integer weight
12. Yearly bonus integer weight
13. Retirement benefits integer weight
14. Leave time integer weight
15. Number of jobs selected to compare

In addition, there are a number of possible choices for each of the 15 categories. 

Job title, company, and location choices may be:
1. Empty [error]
2. Spaces only [error]
3. Alphanumeric (valid)

Job cost of living, commute time, yearly salary, yearly bonus, retirement benefits, and leave time choices may be:
1. Empty [error]
2. Decimal point only [error]
3. Unsigned decimal (valid)

Integer weight choices may be:
1. Empty [error]
2. Zero [error]
3. Unsigned integer (valid)

The number of jobs selected to compare may be:
1. Not two [error]
2. Two

Test cases were generated with the above categories and choices using the TSL Generator application, courtesy of Alex Orso. The total number of unique test cases yielded was 30, but upon further inspection, the choices for each category could be simplified even further. Since all the possible error values for a given field are checked internally, the number of choices can be limited to two: valid and invalid. This would bring the absolute minimum number of unique system test cases to 16, with the first 15 test cases using invalid values and the 16th test case using all valid values. 

| Test Case ID | Test Purpose       | Steps                                                           | Expected Result                            | Actual Result                              | Status |
| ------------ | ------------------ | --------------------------------------------------------------- | ------------------------------------------ | ------------------------------------------ | ------ |
| TC_01        | Fault injection    | Set job title to invalid value (empty)                          | Invalid Title                              | Invalid Title                              | Pass   |
| TC_02        | Fault injection    | Set job company to invalid value (empty)                        | Invalid Company                            | Invalid Company                            | Pass   |
| TC_03        | Fault injection    | Set job location to invalid value (empty)                       | Invalid Location                           | Invalid Location                           | Pass   |
| TC_04        | Fault injection    | Set job cost of living to invalid value (empty)                 | Invalid Cost of Living                     | Invalid Cost of Living                     | Pass   |
| TC_05        | Fault injection    | Set job commute time to invalid value (empty)                   | Invalid Commute Time                       | Invalid Commute Time                       | Pass   |
| TC_06        | Fault injection    | Set job yearly salary to invalid value (empty)                  | Invalid Yearly Salary                      | Invalid Yearly Salary                      | Pass   |
| TC_07        | Fault injection    | Set job yearly bonus to invalid value (empty)                   | Invalid Yearly Bonus                       | Invalid Yearly Bonus                       | Pass   |
| TC_08        | Fault injection    | Set job retirement benefits to invalid value (empty)            | Invalid Retirement Benefits                | Invalid Retirement Benefits                | Pass   |
| TC_09        | Fault injection    | Set job leave time to invalid value (empty)                     | Invalid Leave Time                         | Invalid Leave Time                         | Pass   |
| TC_10        | Fault injection    | Set commute time integer weight to invalid value (empty)        | Invalid Commute Time Integer Weight        | Invalid Commute Time Integer Weight        | Pass   |
| TC_11        | Fault injection    | Set yearly salary integer weight to invalid value (empty)       | Invalid Yearly Salary Integer Weight       | Invalid Yearly Salary Integer Weight       | Pass   |
| TC_12        | Fault injection    | Set yearly bonus integer weight to invalid value (empty)        | Invalid Yearly Bonus Integer Weight        | Invalid Yearly Bonus Integer Weight        | Pass   |
| TC_13        | Fault injection    | Set retirement benefits integer weight to invalid value (empty) | Invalid Retirement Benefits Integer Weight | Invalid Retirement Benefits Integer Weight | Pass   |
| TC_14        | Fault injection    | Set leave time integer weight to invalid value (empty)          | Invalid Leave Time Integer Weight          | Invalid Leave Time Integer Weight          | Pass   |
| TC_15        | Fault injection    | Set number of jobs selected to compare to invalid value (0)     | Success = false                            | Success = false                            | Pass   |
| TC_16        | No faults          | Set current job details to valid values (Amazon)                | Success = true                             | Success = true                             | Pass   |
| TC_17        | No faults          | Set job offer details to valid values (Google)                  | Success = true                             | Success = true                             | Pass   |
| TC_18        | No faults          | Set all integer weights to valid values (1)                     | Success = true                             | Success = true                             | Pass   |
| TC_19        | No faults          | Set number of jobs selected to compare to valid value (2)       | Success = true                             | Success = true                             | Pass   |

For all faults, the common case was selected: leaving UI elements blank. Since the 16th test case was rather large, it was broken into four discrete test cases for maintainability (`TC_16`, `TC_17`, `TC_18`, and `TC_19`). The test cases were executed by right clicking the `SystemTest` class in the Android Studio editor and clicking `Run 'SystemTest'`. TC_01 through TC_14 measured ErrorText messages, and TC_15 through TC_19 measured Toast messages. The results of the tests cases were populated in the table above.
