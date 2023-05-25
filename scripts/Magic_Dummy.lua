var durability = 8;
var cooldown = -1;
var MP = 5;

action fireball()
	if MP >= 2 then
		UI:me(user, 'Cast Fireball!')
		local damage = Util:dice(2,6) + user:getStrength()
		target:damage(damage, 'Fireball')
		MP = MP - 2
		UI:number('MP Remaining: %d', MP)
	else
		UI:message('Not enough MP!')
	end
	
	if MP <= 0 then
		durability = 0
		UI:me(user, 'is depleted!')
	end
!Action

action lightning()
	if MP >= 3 then
		UI:me(user, 'Cast Lightning Bolt!')
		local damage = Util:dice(2,8) + user:getWit()
		target:damage(damage, 'Lightning')
		MP = MP - 3
		UI:number('MP Remaining: %d', MP)
	else
		UI:message('Not enough MP!')
	end
	
	if MP <= 0 then
		durability = 0
		UI:me(user, 'is depleted!')
	end
!Action

action geyser()
	if MP >= 1 then
		UI:me(user, 'Cast Geyser!')
		local damage = Util:dice(1,6) + user:getStrength()
		target:damage(damage, 'Geyser')
		MP = MP - 1
		UI:number('MP Remaining: %d', MP)
	else
		UI:message('Not enough MP!')
	end
	
	if MP <= 0 then
		durability = 0
		UI:me(user, 'is depleted!')
	end
!Action

action leech_seed()
	if MP >= 1 then
		target:addTag('leech_seed.lua', user)
		UI:me(target, 'was Seeded!')
		MP = MP - 1
		UI:number('MP Remaining: %d', MP)
	else
		UI:message('Not enough MP!')
	end
	
	if MP <= 0 then
		durability = 0
		UI:me(user, 'is depleted!')
	end
!Action

action repair()
	UI:message('Repairing...')
	durability = durability + 5
	cooldown = 2
	UI:number('%d turns until ready!', cooldown)
!Action

event beginTurn()
	cooldown = cooldown - 1
	
	if cooldown > 0 then
		UI:number('%d turns until ready!', cooldown)
		user:cantAct()
	end
	
	if cooldown == 0 then
		MP = 5
		UI:message('MP restored!')
		UI:number('MP Remaining: %d', MP)
		cooldown = -1
	end
!Event