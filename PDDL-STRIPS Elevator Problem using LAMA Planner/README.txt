Author: Marios Pafitis (mpafit02)

This project was part of learning the language PDDL/STRIPS for modeling problems.

The problem was to move the workers from one floor of s skyscraper to their destination 
by using some elevators. The elevators could be either slow or fast.

A slow elevator moves one floor at a time either up or down, if the floor is in the limits 
of the skyscraper. For example, if the elevator is at floor 5, it can go either to floor 6
or floor 7.
 
A fast elevator moves ten floors at a time either up or down, if the floor is in the limits 
of the skyscraper. For example, if the elevator is at floor 10, it can go either to floor 0
or at floor 20.

The workers can enter an elevator and leave it when they desire.

Unlimited number of workers can be in the elevator at a time.

This implementation was using the LAMA planner for the certain results at Problem1.pdf and
Problem2.pdf.
