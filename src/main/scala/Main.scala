import korolev.{KorolevServer, StateStorage}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


/**
  * @author Aleksey Fomkin <aleksey.fomkin@gmail.com>
  */
object Main extends App {

  val TodosFile = "todos.txt"

  KorolevServer[State](
    initialState = State(Vector.empty),
    port = 7281,
    initRender = { access =>
      val view = new View(access)
      view.render
    },
    stateStorage = new StateStorage[State] {
      def write(id: String, value: State): Future[Unit] =
        Future(value.writeToFile(TodosFile))
      def read(id: String): Future[Option[State]] =
        Future(Some(State.readFromFile(TodosFile)))
    }
  )
}
