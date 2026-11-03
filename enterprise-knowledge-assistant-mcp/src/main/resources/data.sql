-- Seed data for the in-memory H2 `client_details` table.
-- Runs after Hibernate creates the schema (spring.jpa.defer-datasource-initialization=true).
-- Columns: client_name, client_value (relationship value), no_of_years (tenure), client_type.
-- id is IDENTITY-generated, so it is omitted here.

INSERT INTO client_details (client_name, client_value, no_of_years, client_type) VALUES ('Northstar Logistics', 950000, 12, 'USA');
INSERT INTO client_details (client_name, client_value, no_of_years, client_type) VALUES ('Blue Harbor Financial', 420000, 7, 'USA');
INSERT INTO client_details (client_name, client_value, no_of_years, client_type) VALUES ('Vanguard Healthcare', 1300000, 3, 'USA');
INSERT INTO client_details (client_name, client_value, no_of_years, client_type) VALUES ('Orion Manufacturing', 275000, 15, 'LOCAL');
INSERT INTO client_details (client_name, client_value, no_of_years, client_type) VALUES ('Greenfield Retail', 180000, 2, 'LOCAL');
INSERT INTO client_details (client_name, client_value, no_of_years, client_type) VALUES ('Meridian Supplies', 640000, 9, 'LOCAL');
