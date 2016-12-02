import korolev.EventResult._
import korolev.Korolev.EventFactory
import korolev.{Event, Korolev, KorolevAccess, Shtml}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps

/**
  * @author Aleksey Fomkin <aleksey.fomkin@gmail.com>
  */
class View(access: KorolevAccess[State]) extends Shtml {

  // Handler to input
  val inputId = access.id()

  // Generate actions when clicking checkboxes
  val todoClick: EventFactory[(Int, Todo)] =
    access.event("click", Event.AtTarget) { case (i, todo) =>
      immediateTransition { case state =>
        val updated = state.todos.updated(i, state.todos(i).copy(done = !todo.done))
        state.copy(todos = updated)
      }
    }

  // Generate AddTodo action when 'Add' button clicked
  val addTodoClick: EventFactory[Unit] =
    access.event("submit") { _ =>
      deferredTransition {
        inputId.get[String]('value) map { value =>
          val todo = Todo(value, done = false)
          transition {
            case state =>
              state.copy(todos = state.todos :+ todo)
          }
        }
      }
    }

  // Create a DOM using state
  def render: Korolev.Render[State] = {
    case state =>
      'body(
        'div("Super TODO tracker"),
        'div('style /= "height: 250px; overflow-y: scroll",
          (state.todos zipWithIndex) map {
            case (todo, i) =>
              'div(
                'input(
                  'type /= "checkbox",
                  'checked when todo.done,
                  todoClick(i, todo)
                ),
                if (!todo.done) 'span(todo.text)
                else 'strike(todo.text)
              )
          }
        ),
        'form(addTodoClick(()),
          'input(
            inputId,
            'type /= "text",
            'placeholder /= "What should be done?"
          ),
          'button(
            "Submit"
          )
        )
      )
  }

}
