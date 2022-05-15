package pcd.assignment02.reactive_programming.project_analyzer

import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

object RxEventBus:
  private val channels: Map[ProjectElementType, PublishSubject[String]] = Map(
    ProjectElementType.Package -> PublishSubject.create,
    ProjectElementType.Class -> PublishSubject.create,
    ProjectElementType.Interface -> PublishSubject.create,
    ProjectElementType.Field -> PublishSubject.create,
    ProjectElementType.Method -> PublishSubject.create
  )

  def subscribe(channel: ProjectElementType, consumer: Consumer[String]): Disposable =
    synchronized {
      channels(channel).subscribe(consumer)
    }

  def publish(channel: ProjectElementType, message: String): Unit =
    synchronized {
      channels(channel).onNext(message)
    }
