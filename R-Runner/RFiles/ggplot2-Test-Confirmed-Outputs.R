library(RMySQL)
library("ggplot2")

set.seed(1)
set.seed(1)
df0 <- data.frame(Age = factor(rep(x = 1:10, times = 2)), 
                  Gender = rep(x = c("Female", "Male"), each = 10),
                  Population = sample(x = 1:100, size = 20))

head(df0)
ggplot(data = df0, 
       mapping = aes(x = Age, fill = Gender, 
                     y = ifelse(test = Gender == "Male", 
                                yes = -Population, no = Population))) +
  geom_bar(stat = "identity") +
  scale_y_continuous(labels = abs, limits = max(df0$Population) * c(-1,1)) +
  labs(y = "Population") +
  coord_flip()

ggsave("ggtesters.pdf")