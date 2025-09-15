-- V2__Add_Attributes_To_Application_Table.sql
-- Adds a nullable 'attributes' JSONB column to the 'application' table
ALTER TABLE application
ADD COLUMN attributes JSONB NULL;
