base command to operate

help
Command list is shown below:
help 
show all the command
user -c 
create user by user name and password
user -d 
delete user by user name
user -a 
show all saved user
role -c 
create role by role name
role -d 
delete role by role name
role -a 
show all saved roles
user -add 
add role to user by user name and role name
auth 
authenticate by user name and password  and return a token
auth -i 
invalidate the auth token
role -check 
check roles by auth token and role name
user -a -r 
get all roles for the user thru the auth token
exit 
exit the program
