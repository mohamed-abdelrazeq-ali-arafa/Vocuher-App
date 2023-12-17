# Voucher Processing Application 
# Overview

This Java application is built to process vouchers loaded from a CSV file using Spring Batch and schedule the process using Quartz Scheduler. The application performs various data validations and filters on the loaded voucher data, creating a filtered file for invalid entries. Additionally, it provides CRUD operations for vendor management and voucher operations such as reserve, commit, and rollback. Pagination is implemented to efficiently handle a large number of vouchers.

# Features 
1- Spring Batch Integration: Utilizes Spring Batch for loading vouchers from a CSV file and processing them.

2- Quartz Scheduler: Implements a scheduler to check for new files at fixed time intervals and process them accordingly.

3- Data Validation and Filtering:

4- Checks for validity based on criteria such as passed date validation and maximum length. Creates a filtered file containing invalid entries. CRUD Operations:

5- Vendor Management: Provides CRUD operations for managing vendors.

6- Voucher Operations: Supports operations like reserve, commit, and rollback on vouchers. 6- Pagination: Implements pagination to efficiently handle a large number of vouchers and vendor records.

7-Implement pagination to deal with this millions of record 

8-Implement Caching Layer to help to retrieve data as fast as possible
