SELECT 'ALTER TABLE TBL_BIN DROP CONSTRAINT ' || CONSTRAINT_NAME || ';'
FROM user_constraints WHERE table_name = 'TBL_BIN' AND index_name = 'UK_TBL_BIN'