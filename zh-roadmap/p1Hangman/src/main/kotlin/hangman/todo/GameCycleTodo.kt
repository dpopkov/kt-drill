package learn.zhroadmap.hangman.todo

import learn.zhroadmap.hangman.ICommandInterface
import learn.zhroadmap.hangman.IGameCycle

class GameCycleTodo: IGameCycle {
    override var console: ICommandInterface
        get() = TODO("Not yet implemented")
        set(value) {
            TODO("Not yet implemented")
        }

    override fun start(wordProducer: () -> String) {
        TODO("Not yet implemented")
    }
}
