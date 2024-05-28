# this program sums an array of integers from front to back
COPY R2 1
COPY R3 200
COPY R7 220
LOAD R4 R3
MATH ADD R5 R4
MATH ADD R3 R2
BRANCH LTE R7 R3 -4
HALT
