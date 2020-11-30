delete
from user_detail;
delete
from address;

insert into address(id, street, city, state, postcode)
values (1, '1 Paul Street', 'Coogee', 'NSW', '2031')
     , (2, '2 Paul Street', 'Maroubra', 'NSW', '2039')
     , (3, '3 Paul Street', 'Sydney', 'NSW', '2000')
     , (4, '4 Paul Street', 'North Sydney', 'NSW', '2060')
     , (5, '5 Paul Street', 'St Pauls', 'NSW', '2031')
     , (6, '6 Paul Street', 'Tempe', 'NSW', '2031')
     , (7, '17 Paul Street', 'Bexley', 'NSW', '2031');
insert into user_detail (id, title, first_name, last_name, gender, emp_id, address_id)
values (11, 'Mr', 'John', 'Doe', 'male', '11', 1)
     , (21, 'Mr', 'Tim', 'Pauls', 'male', '11', 2)
     , (31, 'Doctor', 'Bill', 'Boston', 'male', '11', 3)
     , (41, 'Prof', 'Bob', 'Orien', 'male', '11', 4)
     , (51, 'Mr', 'Dohn', 'Coles', 'male', '11', 5)
     , (61, 'Mrs', 'Jil', 'Doros', 'female', '11', 6)
     , (71, 'Ms', 'Jane', 'Doros', 'female', '11', 7);