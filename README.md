# Full Stack Developer (Java & Angular) – Practical Test

## Instructions

### Tech Stack:
- **Backend**: Spring Boot (Java >= 17)  
- **Frontend**: Angular (>=18)  
- **Database**: Microsoft SQL Server  
- **System Architecture**: Your choice  

---

## 1. Frontend – Angular
- **Login Page**: Implement a login page for authentication.  
- **Dashboard**: Display a dashboard with a tile showing the total number of students in the database.  
- **Menu**: The dashboard should include the following menu options:  
  - Data Generation  
  - Data Processing  
  - File Upload  
  - Student Management  
  - Student Report  

---

## 2. Backend – Spring Boot API

- **Security**:  
  - All endpoints, except login, must be protected from unauthorized access.  
  - Use **Spring Security** and **JWT** (JSON Web Tokens) for authentication.  
  - The token should only be generated after a successful login using **username** and **password**.  

- **Test Users**:  
  - Test user data must be pre-generated and stored in the database at startup.  
  - There is no registration feature to be implemented.  

---

## a) Data Generation

- The system should be able to generate **student records** and save them in an Excel document.  
- Provide a UI for entering the number of records to be generated and a button to send a request to the backend.  
- **Data to be generated**:  
  - `studentId`: Numeric (incremental, starting from 1)  
  - `firstName`: Random string (3-8 characters)  
  - `lastName`: Random string (3-8 characters)  
  - `DOB`: Random date between 1st Jan 2000 and 31st Dec 2010  
  - `class`: Random string (Options: Class1, Class2, Class3, Class4, Class5)  
  - `score`: Numeric (random between 55 and 85)  
  - `status`: Numeric (0 for inactive, 1 for active; default is 1)  
  - `photoPath`: String (Default: empty string)  

- **Excel file generation**:  
  - Windows: `C:\var\log\applications\API\dataprocessing\<excel-file-name>`  
  - Linux: Similar path structure  

- **Edge Case Handling**:  
  - **Best case**: If the user enters `0`, generate an Excel file with zero records.  
  - **Worst case**: If the user enters `1,000,000`, generate an Excel file with `1,000,000` records.  

---

## b) Data Processing

- The system should read the generated Excel file and save it in a **CSV** file.  
- Before saving the data, the system should update the student scores by adding `10` to the original score.  
  - `student CSV score = student Excel score + 10`  
- Provide a UI button to initiate the task. When clicked, the system should process the file from the predefined path.  

---

## c) Data Upload

- The system should read the Excel file and upload the data into the **Microsoft SQL Server database**.  
- Before uploading, the system should update the student scores by adding `5` to the original score.  
  - `student database score = student Excel score + 5`  
- Provide a UI button to allow the user to pick the Excel file and upload it for processing.  

---

## d) Report & Export

- Display a list of students with action buttons for **View**, **Edit**, and **Delete**.  
- **View**: Show student details, including the photo. Use an avatar for students without a photo.  
- **Delete**:  
  - Include a confirmation prompt before deletion.  
  - Perform a **soft delete** (Set the status to `0` – inactive).  
- **Update/Edit**:  
  - Allow updating student details, including uploading a new photo (**PNG/JPEG, max size 5MB**).  
  - Rename the file by adding the **student ID** as a prefix and save it in:  
    - Windows: `C:\var\log\applications\API\StudentPhotos\<studentId>-<filename>`  
    - Linux: Similar path  
  - Update the database record with the new **photoPath**.  

---

## e) Student Management – Update/Edit Student

- Provide a **student report** with pagination.  
- Allow users to **export the report to an Excel file**.  
- **Filters** for the student report:  
  - Search by `studentId`  
  - Filter by `class` (Dropdown selection)  
  - Filter by `DOB` (between start and end date) using a date picker  

---

## Submission Guidelines

- **GitHub**: Host your project on GitHub and share the link.  
- **Video**: Record a video (**max 30 minutes**) showcasing your work, explaining your approach, and highlighting optimization techniques for both the application and database.  
- **Worst-Case Scenario**: Demonstrate how the system handles large datasets (e.g., generating **1,000,000** student records).  
- **Challenges**: Discuss any challenges encountered during the project and your solutions.  

---

### Additional Notes:
- Ensure that the application is **robust, scalable, and user-friendly**.  
- Follow **best practices** in coding, security, and database management.  
