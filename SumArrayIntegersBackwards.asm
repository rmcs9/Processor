# this program sums an array of 20 integers from back to front
COPY R2 1
COPY R3 200
COPY R7 219
LOAD R4 R7
MATH ADD R5 R4
MATH SUBTRACT R7 R2
BRANCH LTE R7 R3 -4
HALT
