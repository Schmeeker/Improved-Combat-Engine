var durability = 8;
var cooldown = 0;
var MP = 5;

action fireball()
	if MP > 0 then
		MP = MP - 1
		UI:number('MP Remaining: %d', MP)
	end
	
	if MP == 0 then
		durability = 0
		UI:me(user, 'damaged')
	end
!Action

action lightning()
	if MP > 0 then
		MP = MP - 1
		UI:number('MP Remaining: %d', MP)
	end
	
	if MP == 0 then
		durability = 0
		UI:me(user, 'damaged')
	end
!Action

action geyser()
	if MP > 0 then
		MP = MP - 1
		UI:number('MP Remaining: %d', MP)
	end
	
	if MP == 0 then
		durability = 0
		UI:me(user, 'damaged')
	end
!Action

action leech_seed()
	if MP > 0 then
		MP = MP - 1
		UI:number('MP Remaining: %d', MP)
	end
	
	if MP == 0 then
		durability = 0
		UI:me(user, 'damaged')
	end
!Action

action repair()
	UI:message('Repairing...')
	durability = durability + 5
	cooldown = 2
!Action

event beginTurn()
	cooldown--
	
	if cooldown > 0 then
		UI:number('%d turns until ready!', cooldown)
		user:cantAct()
	end
	
	if cooldown <= 0 then
		cooldown = 0
		MP = 5
		UI:message('MP restored!')
		UI:number('MP Remaining: %d', MP)
		cooldown = 0
	end
!Event