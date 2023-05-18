var charges = 2;
var cooldown = 0;

event attack()
	if charges > 0 then
		charges = charges - 1
		UI:number('Charges Remaining: %d', charges)
	end
	
	if charges == 0 then
		UI:message('The Bug Zapper is Depleted!')
		cooldown = 1
		UI:number('%d turn until ready!', cooldown)
	end
!Event

event beginTurn()
	if cooldown > 0 then
		cooldown--
	end
	
	if cooldown <= 0 then
		charges = 2
		UI:message('The Bug Zapper is Ready!')
		UI:number('Charges Remaining: %d', charges)
		cooldown = 0
	end
!Event
	