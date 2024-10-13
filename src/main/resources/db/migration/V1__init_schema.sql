-- LA.luvina1234
    -- Tạo bảng departments
    CREATE TABLE IF NOT EXISTS `departments` (
        department_id BIGINT AUTO_INCREMENT PRIMARY KEY,
        department_name VARCHAR(50) NOT NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


    -- Tạo bảng certifications
    CREATE TABLE IF NOT EXISTS `certifications` (
        certification_id BIGINT AUTO_INCREMENT PRIMARY KEY,
        certification_name VARCHAR(50) NOT NULL,
        certification_level INT NOT NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;



    -- Tạo bảng employees
    CREATE TABLE IF NOT EXISTS `employees` (
        employee_id BIGINT AUTO_INCREMENT PRIMARY KEY,
        department_id BIGINT NOT NULL,
        employee_name VARCHAR(255) NOT NULL,
        employee_name_kana VARCHAR(255),
        employee_birth_date DATE,
        employee_email VARCHAR(255) NOT NULL,
        employee_telephone VARCHAR(50),
        employee_login_id VARCHAR(50) NOT NULL,
        employee_login_password VARCHAR(100) DEFAULT NULL,
        FOREIGN KEY (department_id) REFERENCES departments(department_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    -- Chèn dữ liệu mẫu vào bảng employees
    INSERT INTO employees (department_id, employee_name, employee_email, employee_login_id, employee_login_password)
    VALUES (1, 'Administrator', 'la@luvina.net', 'admin', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy');

    -- Tạo bảng employees_certifications
    CREATE TABLE IF NOT EXISTS `employees_certifications` (
        employee_certification_id BIGINT AUTO_INCREMENT PRIMARY KEY,
        employee_id BIGINT NOT NULL,
        certification_id BIGINT NOT NULL,
        start_date DATE,
        end_date DATE,
        score DECIMAL(5,2),
        FOREIGN KEY (employee_id) REFERENCES employees(employee_id),
        FOREIGN KEY (certification_id) REFERENCES certifications(certification_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    -- Chèn dữ liệu mẫu vào bảng employees_certifications