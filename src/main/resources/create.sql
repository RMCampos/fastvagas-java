CREATE DATABASE ondetemvagas;
CREATE USER scooby WITH PASSWORD '123';
GRANT ALL PRIVILEGES ON DATABASE ondetemvagas TO scooby;
GRANT ALL ON ALL SEQUENCES IN SCHEMA public TO scooby;
