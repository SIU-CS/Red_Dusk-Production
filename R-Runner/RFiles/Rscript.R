library(RMySQL)

n <- floor(runif(1000)*10)
t <- table(n)

png(filename="somedumbgraph.png")
barplot(t)
dev.off()

n <- floor(runif(1000)*10)
t <- table(n)
barplot(t)

n <- floor(runif(1000)*10)
t <- table(n)
barplot(t)

x <- 10

y <- 25

z <- sum(x,y)



cat("x + y = ", z)
cat("x + y = ", z)

cat("x + y = ", z)