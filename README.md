# FlySasScrape
Chooses date on both callendars.</br>
Selects flight directions. </br>
Disable cookies notification. </br>
Checks if "Show low-price calendar" checkbox is selected and unchecks it.</br>

*AFTER PRESSING SEARCH*

*"SORRY TO DISTURB YOU" window:*
1. Please verify beeing human manually, press resend on alert. 
2. Close window.
3. Start program again.

*"WE’RE VALIDATING YOUR JAVASCRIPT ENGINE"*</br>
Program handles allert itself. Most of the time.</br>
If afer accepting allert it repeats itself - it means website feels intimidated and wont open up for you.</br>
1. change VPN
2. Start program again.

*After*
1. Selects lowest price in table 1 line 1. 
1.1 Does that by checking all price collumns.
2. Jumps to next line if flight is indirect and not through Osl.
3. Does the same with table 2.
4. stores data in list of "Flight" objects.
