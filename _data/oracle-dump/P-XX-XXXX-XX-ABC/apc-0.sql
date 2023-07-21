SELECT 'ALTER TABLE TBL_APC DROP CONSTRAINT ' || CONSTRAINT_NAME || ';'
FROM user_constraints WHERE table_name = 'TBL_APC' AND index_name = 'UK_TBL_APC'