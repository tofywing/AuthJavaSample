This project is to simulation the authenticate operation

Enter ‘help’ for the command list.

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

authenticate by user name and password and return a token

auth -i

invalidate the auth token

role -check

check roles by auth token and role name

user -a -r

get all roles for the user thru the auth token

exit

exit the program

![](media/image1.png)

Provide a few examples ,

Create user

‘user -c’ then follow the instructions.

![](media/image2.png)

Authenticate

‘auth’

![](media/image3.png)

Check roles

‘role -check’

![](media/image4.png)

You may also can enter command during the main operation.

e.g. you need to check the token during the “check roles” operations by
entering ‘token -a’ to print the token report. Therefor you can find the
exact token that matches the specific username.

![](media/image5.png)

Enter ‘exit’ anytime to step over your current operation, and the
program will exist if you are at the main menu.

![](media/image6.png)

![](media/image7.png)

![](media/image8.png)![](media/image9.png)
