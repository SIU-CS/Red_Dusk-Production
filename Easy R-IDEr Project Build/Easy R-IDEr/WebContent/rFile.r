# Filename: ProgrammingBasics.R
 
# ---Simple Calculations---
2 + 3
 
x <- 2
y <- 3
x + y
x * y
 
# ---Data Structures---
 
# Vectors
workshop <- c(1, 2, 1, 2, 1, 2, 1, 2)
print(workshop)
workshop
 
gender <- c("f", "f", "f", NA, "m", "m", "m", "m")
q1 <- c(1, 2, 2, 3, 4, 5, 5, 4)
q2 <- c(1, 1, 2, 1, 5, 4, 3, 5)
q3 <- c(5, 4, 4,NA, 2, 5, 4, 5)
q4 <- c(1, 1, 3, 3, 4, 5, 4, 5)
 
# Selecting Elements of Vectors
q1[5]
q1[ c(5, 6, 7, 8) ]
q1[5:8]
q1[gender == "m"]
mean( q1[ gender == "m" ], na.rm = TRUE)
 
# ---Factors---
 
# Numeric Factors
 
# First, as a vector
workshop <- c(1, 2, 1, 2, 1, 2, 1, 2)
workshop
table(workshop)
mean(workshop)
gender[workshop == 2]
 
# Now as a factor
workshop <- c(1, 2, 1, 2, 1, 2, 1, 2)
workshop <- factor(workshop)
workshop
table(workshop)
mean(workshop) #generates error now.
gender[workshop == 2]
gender[workshop == "2"]
 
# Recreate workshop, making it a factor
# including levels that don't yet exist.
workshop <- c(1, 2, 1, 2, 1, 2, 1, 2)
workshop <- factor(
workshop,
levels = c( 1,   2,     3,      4),
labels = c("R", "SAS", "SPSS", "Stata")
)
 
# Recreate it with just the levels it
# curently has.
workshop <- c(1, 2, 1, 2, 1, 2, 1, 2)
workshop <- factor(
workshop,
levels = c( 1,  2),
labels = c("R","SAS")
)
 
workshop
table(workshop)
gender[workshop == 2]
gender[workshop == "2"]
gender[workshop == "SAS"]
 
# Character factors
 
gender <- c("f", "f", "f", NA, "m", "m", "m", "m")
gender <- factor(
gender,
levels = c("m",    "f"),
labels = c("Male", "Female")
)
 
gender
table(gender)
workshop[gender == "m"]
workshop[gender == "Male"]
 
# Recreate gender and make it a factor,
# keeping simpler m and f as labels.
gender <- c("f", "f", "f", NA, "m", "m", "m", "m")
gender <- factor(gender)
gender
 
# Data Frames
mydata <- data.frame(workshop, gender, q1, q2, q3, q4)
mydata
 
names(mydata)
row.names(mydata)
 
# Selecting components by index number
mydata[8, 6] #8th obs, 6th var
mydata[ , 6] #All obs, 6th var
mydata[ , 6][5:8] #6th var, obs 5:8
 
# Selecting components by name
mydata$q1
mydata$q1[5:8]
 
# Example renaming gender to sex while
# creating a data frame (left as a comment)
#
# mydata <- data.frame(workshop, sex = gender,
#   q1, q2, q3, q4)
 
# Matrices
 
# Creating from vectors
mymatrix <- cbind(q1, q2, q3, q4)
mymatrix
dim(mymatrix)
 
# Creating from matrix function
# left as a comment so we keep
# version with names q1, q2...
#
# mymatrix <- matrix(
#   c(1, 1, 5, 1,
#     2, 1, 4, 1,
#     2, 2, 4, 3,
#     3, 1, NA,3,
#     4, 5, 2, 4,
#     5, 4, 5, 5,
#     5, 3, 4, 4,
#     4, 5, 5, 5),
#  nrow = 8, ncol = 4, byrow = TRUE)
# mymatrix
 
table(mymatrix)
mean(mymatrix, na.rm = TRUE)
cor(mymatrix, use = "pairwise")
 
# Selecting Subsets of Matrices
 
mymatrix[8, 4]
mymatrix[5:8, 3:4]
mymatrix[ ,4][1:4]
mymatrix$q4  # No good!
mymatrix[ ,"q4"]
 
# Matrix Algebra
 
mymatrixT <- t(mymatrix)
mymatrixT
 
# Lists
mylist <- list(workshop, gender,
q1, q2, q3, q4, mymatrix)
mylist
 
# List, this time adding names
mylist <- list(
workshop = workshop,
gender   = gender,
q1 = q1,
q2 = q2,
q3 = q3,
q4 = q4,
mymatrix = mymatrix)
mylist
 
# Selecting components by index number.
mylist[[2]]
mylist[[2]][5:8]
 
mylist[2]
mylist[2][5:8]  # Bad!
 
# Selecting components by name.
mylist$q1
mylist$mymatrix[5:8, 3:4]
 
# ---Saving Your Work---
 
ls()
objects() #same as ls()
 
save.image("myall.RData")
save(mydata, file = "mydata.RData")
 
# The 2nd approach is commented to keep
# the q variables for following examples.
# rm(x, y, workshop, gender, q1, q2, q3, q4, mylist)
# ls()
# save.image(file = "mydata.RData")
 
# ---Comments to Document Your Programs---
 
# This comment is on its own line, between functions.
 
workshop <- c(1, 2, 1, 2, #This comment is within the arguments.
1, 2, 1, 2)  # And this is at the end.
 
# ---Comments to Document Your Objects---
 
comment(mydata) <- "Example data from R for SAS and SPSS Users"
comment(mydata)
 
# ---Controlling Functions---
 
# Controlling Functions with Arguments
 
help("mean")
mean(x = q3, trim = .25, na.rm = TRUE)
mean(na.rm = TRUE, x = q3, trim = .25)
mean(q3, .25, TRUE)
mean(q3, t = .25, na.rm = TRUE)
mean(1, 2, 3)
mean( c(1, 2, 3) )
mean( 1:3 )
 
# Controlling Functions With Formulas
 
lm( q4 ~ q1 + q2 + q3, data = mydata )
 
t.test(q1 ~ gender, data = mydata)
 
t.test( q1[ which(gender == "Female") ],
q1[ which(gender == "Male")   ],
data = mydata)  # Data ignored!
 
# Controlling Functions with Extractor Functions
 
lm( q4 ~ q1 + q2 + q3, data = mydata )
 
myModel <- lm( q4 ~ q1 + q2 + q3, data = mydata )
class(myModel)
summary(myModel)
 
# How Much Output Is There?
 
print(mymodel)
 
mode(myModel)
class(myModel)
names(myModel)
print( unclass(myModel) )
 
myModel$coefficients
class( myModel$coefficients )
barplot( myModel$coefficients )
 
# ---Writing Your Own Functions (Macros)---
 
myvar <- c(1, 2, 3, 4, 5)
 
# A bad function.
mystats <- function(x) {
mean(x, na.rm = TRUE)
sd(x, na.rm = TRUE)
}
 
mystats(myvar)
 
# A good function that just prints.
mystats <- function(x) {
print( mean(x, na.rm = TRUE) )
print(   sd(x, na.rm = TRUE) )
}
mystats(myvar)
 
# A function with vector output.
mystats  <- function(x) {
mymean <- mean(x, na.rm = TRUE)
mysd   <-   sd(x, na.rm = TRUE)
c(mean = mymean, sd = mysd )
}
mystats(myvar)
myVector <- mystats(myvar)
myVector
 
# A function with list output.
mystats   <- function(x) {
myinput <- x
mymean  <- mean(x, na.rm = TRUE)
mysd    <-   sd(x, na.rm = TRUE)
list(data = myinput, mean = mymean, sd = mysd)
}
mystats(myvar)
myStatlist <- mystats(myvar)
myStatlist
mystats
 
save(mydata, mymatrix, mylist, mystats,
file = "myWorkspace.RData")
