# this program sums a linked list of integers from head to tail
COPY R1 352
LOAD R2 R1
MATH ADD R3 R2
LOAD R1 1
LOAD R2 R1
BRANCH NE R2 R0 -4
HALT
