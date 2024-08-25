CREATE TABLE student_vacancy (
    student_id TEXT,
    vacancy_id TEXT,
    PRIMARY KEY (student_id, vacancy_id),
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (vacancy_id) REFERENCES vacancies(id)
);