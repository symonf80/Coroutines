import kotlinx.coroutines.*
import kotlin.coroutines.EmptyCoroutineContext


// ------Cancellation------

// � 1:
fun main() = runBlocking {
    val job = CoroutineScope(EmptyCoroutineContext).launch {
        launch {
            delay(500)
            println("ok") // <-- �� ����������
        }
        launch {
            delay(500)
            println("ok")
        }
    }
    delay(100)
    job.cancelAndJoin()
}
/** ��������� ��� �������� �������� ������ ������������. ��� ��� �������� ��������� �������� � 0.5 ���. � ������� OK.
��� ��� �� ����������� ������������ � ����� OK ����� ����������� �����������. ����� �������� ���� �������,
���������� delay(100) � ��������, ��� �������� �������� ����� ����� 100 �.���. ����� ������ job.cancelAndJoin,
Job ����� �������, � ��� ��������, ���������� ������ Job, ���� ����� ��������. ������ � ������, ���������� � ����
�������� //<-- �� ����������, ��� ��� �� ������ �����������. */
//=====================================================================================================================

// � 2:
//fun main() = runBlocking {
//    val job = CoroutineScope(EmptyCoroutineContext).launch {
//        val child = launch {
//            delay(500)
//            println("ok") // <-- �� ����������
//        }
//        launch {
//            delay(500)
//            println("ok")
//        }
//        delay(100)
//        child.cancel()
//    }
//    delay(100)
//    job.join()
//}
/** ��������� ��� �������� �������� ������ ������������. ������ �������� child ����� �������� � 0.5 ���. � ������� OK.
������ �������� ����� ��������� �������� � 0.5 ���. � ������� OK, �� ��� �� ����� ��������� � child.
����� ���� ������� ���������� delay(100), ��� ��������, ��� ������������ �������� ����� ������� 100 �.���., ����� ���,
��� ��������� child.cancel. ����� ������ child.cancel(), �������� child ����� ��������, ��� ��� ����� cancel()
����������� ������ � ���� ��������. ����� �������, ��� � ������ � �������� //<-- �� ����������, ��� ��� �������� child
����� �������� �� ���������� �������� � ������ OK. */

//======================================================================================================================

//------Exception Handling------
 // � 1:
//fun main() {
//    with(CoroutineScope(EmptyCoroutineContext)) {
//        try {
//            launch {
//                throw Exception("something bad happened")
//            }
//        } catch (e: Exception) {
//            e.printStackTrace() // <-- �� ����������
//        }
//    }
//    Thread.sleep(1000)
//}
/** �������� �������� � ����� launch, � � ���� �������� ������������� ���������� "something bad happened".
��� ���������� ������������� ������ ���� ��������, � ���� catch ��������� ��� ���� ��������. ����������, ��� ����������
����� ���������� ������ ��������, ���������� ����� ��������� � ���� ��������, �� ��� � ����� catch �� ����������,
��� ��� �������� ����� ��������� ��-�� ������������ ����������. ����� ������� ������ � ���� � �������� //<--
�� ����������, ��� ��� ���������� ����� ������� ������ ��������, � �� ��� �. */
//=====================================================================================================================

// � 2:
//fun main() {
//    CoroutineScope(EmptyCoroutineContext).launch {
//        try {
//            coroutineScope {
//                throw Exception("something bad happened")
//            }
//        } catch (e: Exception) {
//            e.printStackTrace() // <-- ����������
//        }
//    }
//    Thread.sleep(1000)
//}
/** �������� �������� � ����� launch, � � ���� �������� ���������� coroutineScope, ������� ��������� ������� Scope
��� ��������� ������ �� ��������� ���������. ������ coroutineScope ������������� ���������� "something bad happened".
� ����� catch ���������� ����� ������� �������. ����� ���� ��� ���������� ������� ������ e.printStackTrace ����� �������
� ������� ���� ������� � ���������� �� ����������. ����� ���������� ��������, ���������� ����� ������ �� 1 ���.,
��� ���� �������� ���������� ������� ��� ����������. ��� � ������� � �������� //<-- ����������. */
//=====================================================================================================================

// � 3:
//fun main() {
//    CoroutineScope(EmptyCoroutineContext).launch {
//        try {
//            supervisorScope {
//                throw Exception("something bad happened")
//            }
//        } catch (e: Exception) {
//            e.printStackTrace() // <-- ����������
//        }
//    }
//    Thread.sleep(1000)
//}
/** �������� �������� � ����� launch. ������ ���� �������� ���������� supervisorScope, ������� ����� ������� �����
�������� (supervisor), ��� �������� ���� ������ ���� �������� ���������� ����������, �� ��� �� �������� � ������
������������ ��������. ������ supervisorScope ��������� ���������� "something bad happened". ����� ��� ����������
��������������� � ������� catch � ��������� � ������� e.printStackTrace. ����� ����� ���������� Thread.sleep(1000),
��� ���������������� �������� ����� �� 1 �������, ��� ��� ��� ���� �������� ��������� ����� ����������� ��
���������� ���������� ��������. ������ ��� � ������� � �������� //<-- ����������. */
//=====================================================================================================================

// � 4:
//fun main() {
//    CoroutineScope(EmptyCoroutineContext).launch {
//        try {
//            coroutineScope {
//                launch {
//                    delay(500)
//                    throw Exception("something bad happened") // <-- �� ����������
//                }
//                launch {
//                    throw Exception("something bad happened")
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//    Thread.sleep(1000)
//}
/** �������� �������� � ������� launch, � ������ ���� �������� ���������� coroutineScope. ������ coroutineScope
�������� ��� ��������. ������ �������� ��������� �������� � 0.5 ���. � ����� ������� ����������
"something bad happened". ������ �������� ����� ������� ���������� "something bad happened" � ��� ���������� �����
����������� ������ catch, ������� ��������� ������ ������� ��������. ������ �������� � ��������� ����������� ����������
����� ������ �������. ��� ���������� ����� ��������� ������ ���� �������� � �� ����� ���������� ����� � ������ ����.
����� �������, ��� � ������ � �������� //<-- �� ����������. */
//=====================================================================================================================
// � 5:
//fun main() {
//    CoroutineScope(EmptyCoroutineContext).launch {
//        try {
//            supervisorScope {
//                launch {
//                    delay(500)
//                    throw Exception("something bad happened") // <-- ����������
//                }
//                launch {
//                    throw Exception("something bad happened")
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace() // <-- ����������
//        }
//    }
//    Thread.sleep(1000)
//}
/** �������� �������� � ����� launch. ������ ���� �������� ���������� supervisorScope. � supervisorScope ������� ���
��������. ������ �������� ��������� �������� � 0.5 ���. � ����� ������� ���������� "something bad happened".
��� ��� � ������ �������� ���� delay � ���������, �� ��� ������������������ �� ����� ������ supervisorScope. �����
��������� ��������, ���������� ��������� � ������� �������� � ���� catch ������ ������������ ��������. ����������,
��� ��� � �������� � ��������� //<-- ����������. */
//======================================================================================================================

// � 6:
//fun main() {
//    CoroutineScope(EmptyCoroutineContext).launch {
//        CoroutineScope(EmptyCoroutineContext).launch {
//            launch {
//                delay(1000)
//                println("ok") // <-- �� ����������
//            }
//            launch {
//                delay(500)
//                println("ok")
//            }
//            throw Exception("something bad happened")
//        }
//    }
//    Thread.sleep(1000)
//}
/** �������� �������� ������ CoroutineScope. ������ ���� �������� ���� ��� ���� �������������� ��������, � �������
��������� ��� �������� �������� � ��������� 1 ���. � 0.5 ���. ����� �������� ������ �������� �������� ������� ������
throw Exception(). ���������� ������������� ������ ������ �������������� ��������. ��, ��������� ���������� ��������
�������� ���� �������������� �� 1 �������, ���������� �� ������������� �����, � ������ ����� ���� ��������. ���� ��� �
������ launch ����������� ���������� � �������� ����� �� ������� ��� ����������. �������, ����� ������� ������� �
�������� 1 ������� � ������� Thread.sleep(1000), ��������� �����������, �� ��������� ���������� ���� �������.
����� �������, ��� � ������ � �������� //<-- �� ����������. */
//=====================================================================================================================
// � 7:
//fun main() {
//    CoroutineScope(EmptyCoroutineContext).launch {
//        CoroutineScope(EmptyCoroutineContext + SupervisorJob()).launch {
//            launch {
//                delay(1000)
//                println("ok") // <-- �� ����������
//            }
//            launch {
//                delay(500)
//                println("ok")
//            }
//            throw Exception("something bad happened")
//        }
//    }
//    Thread.sleep(1000)
//}
/** �������� �������� ������ CoroutineScope. ������ ���� �������� ��������� ��� ���� �������������� ��������
� ����������� SupervisorJob - ������� ��������, ��� ���� ���� �� �������� ������� ����������� ����������, ��� �� ������
�� ���������� ������ �������� �������. ������, � ������ ������, �� ������� �������� �������� ��� SupervisorJob, �������
��� ��� ����� ������ �� ���������� ���� �������� �������. ������ �������������� �������� ��������� ��� ��������:
���� � ��������� 1 ���., ������ � ��������� 0.5 ���. ����� �������� ���� ������� ������� ������ throw Exception().
���������� ������������� ������ �������������� ��������, � ��� ��� ��� �������� �� ����� ������ SupervisorJob,
��� ������ �� ���������� ���� �������, ��������� ������ CoroutineScope � SupervisorJob. ���������� ������ �� ���
�������� ��������: ������ ��������, ������� ������ ���� ��������� println("ok"), � ������. ��������� ����������� �
�����������, � ������ println("ok") �� ����� ���������. ������ ��� � ������� � �������� //<-- �� ����������.
*/