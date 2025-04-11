# 🛠️ Backend Design Decisions for *Stackademic*

## 1. Technology Stack: Spring Boot with Java

**✅ Decision:**  
Chose Spring Boot with Java as the backend framework.

**💡 Why:**
- Familiar tech stack that allowed rapid prototyping.
- Spring Boot reduces boilerplate and supports microservices. Plus, it's what you had requested for use
- Rich ecosystem (e.g., Spring Security, JPA (especially)).

**🚀 Impact:**
- Fast REST API development.
- Clean integration of DB, security, and file processing.

---

## 2. RESTful API Design

**✅ Decision:**  
Structured backend as RESTful APIs.

**💡 Why:**
- Clear separation between frontend (Angular) and backend.
- Used standard HTTP methods and JSON format.
- Stateless design supports scalability.

**🚀 Impact:**
- Smooth frontend-backend communication.
- Future mobile or 3rd-party integrations made easier.

---

## 3. Database: Relational DB + JPA (Hibernate)

**✅ Decision:**  
Used a relational database (e.g., MariDB, ni fork ya mysql so we good) with Spring Data JPA and Hibernate.

**💡 Why:**
- Student data is relational and structured.
- JPA simplifies DB ops using repository methods.
- Hibernate handles transactions and mappings efficiently.

**🚀 Impact:**
- Smooth Excel data storage and report generation.
- Maintains data integrity with less boilerplate.

---

## 4. Authentication: JWT-Based Auth

**✅ Decision:**  
Implemented stateless JWT authentication.

**💡 Why:**
- Self-contained, scalable auth method.
- Spring Security handles backend protection.
- Frontend can securely store and send token.

**🚀 Impact:**
- Protected sensitive endpoints (e.g., report generation).
- Foundation laid for role-based auth later.

---

## 5. Custom Response Wrapper: `EntityResponse`

**✅ Decision:**  
Created a consistent response format wrapper.

**💡 Why:**
- Unified structure for success/error responses.
- Easy frontend handling and debugging.
- `entity` field keeps payloads flexible.

**🚀 Impact:**
- Consistent API responses.
- Clear messages for users and developers.

---

## 6. File Processing: Apache POI for Excel

**✅ Decision:**  
Used Apache POI for Excel file generation and parsing.

**💡 Why:**
- Powerful and reliable library.
- Already familiar and easy to integrate.
- Ideal for `.xlsx` support.

**🚀 Impact:**
- Handles data generation, uploads, and reports.
- Stable and efficient for small to medium datasets.

---

## 7. File Upload: `MultipartFile` + `FormData`

**✅ Decision:**  
Used Spring’s `MultipartFile` with Angular `FormData`.

**💡 Why:**
- Standard approach to handle file uploads.
- Validations added for file type and size.
- Easy frontend integration.

**🚀 Impact:**
- Seamless file uploads.
- User-friendly error feedback on upload issues.

---

## 8. Service Layer: Separation of Concerns

**✅ Decision:**  
Moved business logic into service classes.

**💡 Why:**
- Keeps controllers lean.
- Improves reusability and modularity.
- Easier testing and debugging.

**🚀 Impact:**
- Cleaner code structure.
- Scalability and maintainability improved.

---

## 9. Error Handling: Try-Catch + `EntityResponse`

**✅ Decision:**  
Handled exceptions using try-catch with custom response messages.

**💡 Why:**
- Prevents unhandled errors from crashing the app.
- User gets meaningful, actionable messages.
- Maintains consistent response structure.

**🚀 Impact:**
- Better developer debugging.
- Improved user experience on failures.

---

## 10. File Downloads: `ResponseEntity<byte[]>`

**✅ Decision:**  
Returned downloadable files with `ResponseEntity<byte[]>`.

**💡 Why:**
- Offers control over headers (e.g., `Content-Disposition`).
- Clean way to serve binary files via Spring Boot.

**🚀 Impact:**
- Supported clean download of Excel reports and data.
- Enabled browser to handle files properly.

---
