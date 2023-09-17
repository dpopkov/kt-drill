package learn.zhroadmap.hangman

interface IGameCycle {
    var console: ICommandInterface

    fun start(wordProducer: () -> String)
}
