INSERT INTO tb_course(id, name, description, image_url, creation_date, last_update_date, status, course_level, instructor_id)VALUES(UUID(), 'Java', 'Basic to advanced.', 'www.image.com', NOW(), NOW(), 'CONCLUDED', 'BEGINNER', UUID());
INSERT INTO tb_course(id, name, description, image_url, creation_date, last_update_date, status, course_level, instructor_id)VALUES(UUID(), 'Spring', 'Basic to advanced.', 'www.image.com', NOW(), NOW(), 'CONCLUDED', 'BEGINNER', UUID());
INSERT INTO tb_course(id, name, description, image_url, creation_date, last_update_date, status, course_level, instructor_id)VALUES(UUID(), 'Relational database', 'Basic to advanced.', 'www.image.com', NOW(), NOW(), 'CONCLUDED', 'BEGINNER', UUID());

SET @course1_id = (SELECT id FROM tb_course WHERE name = 'Java');
SET @course2_id = (SELECT id FROM tb_course WHERE name = 'Spring');
SET @course3_id = (SELECT id FROM tb_course WHERE name = 'Relational database');

INSERT INTO tb_module(id, title, description, creation_date, last_update_date, course_id)VALUES(UUID(), 'Java Module 01', 'Introduction to OOP in Java.', NOW(), NOW(), @course1_id);
INSERT INTO tb_module(id, title, description, creation_date, last_update_date, course_id)VALUES(UUID(), 'Java Module 02', 'Data structure.', NOW(), NOW(), @course1_id);
INSERT INTO tb_module(id, title, description, creation_date, last_update_date, course_id)VALUES(UUID(), 'Spring Module 01', 'Introduction to the Spring ecosystem.', NOW(), NOW(), @course2_id);
INSERT INTO tb_module(id, title, description, creation_date, last_update_date, course_id)VALUES(UUID(), 'Relational database Module 01', 'Introduction to SQL commands.', NOW(), NOW(), @course3_id);

SET @module1_id = (SELECT id FROM tb_module WHERE title = 'Java Module 01');
SET @module2_id = (SELECT id FROM tb_module WHERE title = 'Java Module 02');
SET @module3_id = (SELECT id FROM tb_module WHERE title = 'Spring Module 01');
SET @module4_id = (SELECT id FROM tb_module WHERE title = 'Relational database Module 01');

INSERT INTO tb_lesson(id, title, description, video_url, creation_date, last_update_date, module_id)VALUES(UUID(), 'OOP pillar', 'Abstraction.', 'www.video.com', NOW(), NOW(), @module1_id);
INSERT INTO tb_lesson(id, title, description, video_url, creation_date, last_update_date, module_id)VALUES(UUID(), 'OOP pillar', 'inheritance.', 'www.video.com', NOW(), NOW(), @module1_id);
INSERT INTO tb_lesson(id, title, description, video_url, creation_date, last_update_date, module_id)VALUES(UUID(), 'OOP pillar', 'encapsulation.', 'www.video.com', NOW(), NOW(), @module1_id);
INSERT INTO tb_lesson(id, title, description, video_url, creation_date, last_update_date, module_id)VALUES(UUID(), 'OOP pillar', 'polymorphism.', 'www.video.com', NOW(), NOW(), @module1_id);
INSERT INTO tb_lesson(id, title, description, video_url, creation_date, last_update_date, module_id)VALUES(UUID(), 'Data structure', 'Arrays', 'www.video.com', NOW(), NOW(), @module2_id);
INSERT INTO tb_lesson(id, title, description, video_url, creation_date, last_update_date, module_id)VALUES(UUID(), 'Spring Framework', 'Spring Framework fundamentals and Core Container project.', 'www.video.com', NOW(), NOW(), @module3_id);
INSERT INTO tb_lesson(id, title, description, video_url, creation_date, last_update_date, module_id)VALUES(UUID(), 'MySQL database', 'Main SQL commands.', 'www.video.com', NOW(), NOW(), @module4_id);
