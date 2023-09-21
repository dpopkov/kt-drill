package learn.crkotlin.learn.crkotlin.p2c1coroutinebuilders

import kotlinx.coroutines.*
import learn.crkotlin.learn.common.log
import learn.crkotlin.learn.common.logNamed
import learn.crkotlin.learn.common.printMillisInBackground

fun main() {
    printMillisInBackground()

    f01usingCoroutineScope()
}

private fun f01usingCoroutineScope() {
    val token = "123"
    runBlocking {
        val articles: List<ArticleJson> = getArticlesForUser(token)
        log(articles)
    }
    /*
Output:
0 [main] [null] started async
[main] [null] receiving user
[main] [null] starting async
250 500 750 1000 1250 1500 1750 2000 2250 2500 2750 3000 3250 3500 3750 [main] [null] received user
4000 [main] [ArticleJson(json=123)]
     */
}

/*
coroutineScope это suspending функция, которая создает scope для своего lambda выражения.
Функция возвращает то, что возвращает ее lambda.
coroutineScope используется, когда нужен scope внутри suspending функции.
 */
private suspend fun getArticlesForUser(token: String): List<ArticleJson> = coroutineScope {
    val articles: Deferred<List<ArticleJson>> = async {
        logNamed("starting async")
        ArticleRepository.getArticles()
    }
    logNamed("started async")
    logNamed("receiving user")
    val user: User = UserService.getUser(token)
    logNamed("received user")
    articles.await()
        .filter { canSeeOnList(user, it) }
}

private fun canSeeOnList(user: User, articleJson: ArticleJson): Boolean {
    return articleJson.json.contains(user.token)
}

private data class ArticleJson(val json: String)

private object ArticleRepository {
    suspend fun  getArticles(): List<ArticleJson> {
        delay(2000L)
        return listOf<ArticleJson>(
            ArticleJson("123"),
            ArticleJson("345"),
            ArticleJson("567"),
        )
    }
}

private object UserService {
    suspend fun getUser(token: String): User {
        delay(4000L)
        return User(token)
    }
}

private data class User(val token: String)