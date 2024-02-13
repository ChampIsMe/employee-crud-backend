# Dev In progress.  You can run `bash deploy_locally.sh` and view at http://localhost:8082/swagger-ui/index.html

#### Allow function creators:

```mysql
SET GLOBAL log_bin_trust_function_creators = ON;
```
#### Create MYSQL stored function for generating Employee ID

```mysql
USE `empcrud`;
DROP function IF EXISTS `generate_emp_id`;

DELIMITER $$
USE `empcrud`$$
CREATE FUNCTION `generate_emp_id` ()
RETURNS INTEGER
BEGIN
    DECLARE random_number INT;
    REPEAT
        SET random_number = FLOOR(1000 + RAND() * 9000);
    UNTIL NOT EXISTS (
        SELECT * FROM employee WHERE emp_id = random_number
    ) END REPEAT;
    RETURN random_number;
RETURN 1;
END$$

DELIMITER ;
```
