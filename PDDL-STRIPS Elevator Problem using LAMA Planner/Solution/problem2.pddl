(define (problem pb1)
	(:domain lifts)
	(:objects f0 f1 f2 f3 f4 f5 f6 f7 f8 f9 f10 f11 f12 f13 f14 f15 f16 f17 f18 f19 f20 f21 w1 w2 w3 w4 w5 w6 w7 w8 l1 l2 l3 l4 l5 l6 l7 l8)
	(:init 
		(worker w1 f0)
		(worker w2 f1)
		(worker w3 f2)
		(worker w4 f3)
		(worker w5 f4)
		(worker w6 f5)
		(worker w7 f6)
		(worker w8 f7)
        (slow l1 f0)
        (slow l2 f0)
        (slow l3 f0)
        (slow l4 f0)
        (fast l5 f0)
        (fast l6 f0)
        (fast l7 f0)
        (fast l8 f0)
        (move_one f0 f1)
        (move_one f1 f2)
        (move_one f2 f3)
        (move_one f3 f4)
        (move_one f4 f5)
        (move_one f5 f6)
        (move_one f6 f7)
        (move_one f7 f8)
        (move_one f8 f9)
        (move_one f9 f10)
        (move_one f10 f11)
        (move_one f11 f12)
        (move_one f12 f13)
        (move_one f13 f14)
        (move_one f14 f15)
        (move_one f15 f16)
        (move_one f16 f17)
        (move_one f17 f18)
        (move_one f18 f19)
        (move_one f19 f20)
        (move_one f20 f21)
        (move_one f21 f20)
        (move_one f20 f19)
        (move_one f19 f18)
        (move_one f18 f17)
        (move_one f17 f16)
        (move_one f16 f15)
        (move_one f15 f14)
        (move_one f14 f13)
        (move_one f13 f12)
        (move_one f12 f11)
        (move_one f11 f10)
        (move_one f10 f9)
        (move_one f9 f8)
        (move_one f8 f7)
        (move_one f7 f6)
        (move_one f6 f5)
        (move_one f5 f4)
        (move_one f4 f3)
        (move_one f3 f2)
        (move_one f2 f1)
        (move_one f1 f0)
        (move_ten f0 f10)
        (move_ten f10 f20)
        (move_ten f20 f10)
        (move_ten f10 f0)
	)
	(:goal 	(and (worker w1 f10)
		(worker w2 f5)
		(worker w3 f5)
		(worker w4 f5)
		(worker w5 f9)
		(worker w6 f19)
		(worker w7 f17)
		(worker w8 f21))
	)
)
