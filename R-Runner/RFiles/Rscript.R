

n <- floor(runif(1000)*10)
t <- table(n)

png(filename="somedumbgraph.png")
barplot(t)
dev.off()