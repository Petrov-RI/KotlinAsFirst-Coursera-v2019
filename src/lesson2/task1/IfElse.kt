@file:Suppress("UNUSED_PARAMETER")

package lesson2.task1

import lesson1.task1.discriminant
import kotlin.math.max
import kotlin.math.sqrt

// Урок 2: ветвления (здесь), логический тип (см. 2.2).
// Максимальное количество баллов = 6
// Рекомендуемое количество баллов = 5
// Вместе с предыдущими уроками = 9/12

fun main(args: Array<String>) {
    val result = timeForHalfWay(0.0, 0.0, 0.0, 2.0, 0.016666666666666666, 1.0)
    println(result)
}

fun sqr(x: Double): Double = x * x

fun modul(x: Int): Int = if (x < 0) -x else x


fun max(x: Double, y: Double, z: Double): Double = if ((x >= y) && (x >= z)) x else if ((y >= z) && (y >= x)) y else z

/**
 * Пример
 *
 * Найти число корней квадратного уравнения ax^2 + bx + c = 0
 */
fun quadraticRootNumber(a: Double, b: Double, c: Double): Int {
    val discriminant = discriminant(a, b, c)
    return when {
        discriminant > 0.0 -> 2
        discriminant == 0.0 -> 1
        else -> 0
    }
}

/**
 * Пример
 *
 * Получить строковую нотацию для оценки по пятибалльной системе
 */
fun gradeNotation(grade: Int): String = when (grade) {
    5 -> "отлично"
    4 -> "хорошо"
    3 -> "удовлетворительно"
    2 -> "неудовлетворительно"
    else -> "несуществующая оценка $grade"
}

/**
 * Пример
 *
 * Найти наименьший корень биквадратного уравнения ax^4 + bx^2 + c = 0
 */
fun minBiRoot(a: Double, b: Double, c: Double): Double {
    // 1: в главной ветке if выполняется НЕСКОЛЬКО операторов
    if (a == 0.0) {
        if (b == 0.0) return Double.NaN // ... и ничего больше не делать
        val bc = -c / b
        if (bc < 0.0) return Double.NaN // ... и ничего больше не делать
        return -sqrt(bc)
        // Дальше функция при a == 0.0 не идёт
    }
    val d = discriminant(a, b, c)   // 2
    if (d < 0.0) return Double.NaN  // 3
    // 4
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    val y3 = max(y1, y2)       // 5
    if (y3 < 0.0) return Double.NaN // 6
    return -sqrt(y3)           // 7
}

/**
 * Простая (2 балла)
 *
 * Мой возраст. Для заданного 0 < n < 200, рассматриваемого как возраст человека,
 * вернуть строку вида: «21 год», «32 года», «12 лет».
 */
fun ageDescription(age: Int): String {
    val lastNum = age % 10
    return when {
        age in 10..20 -> "$age лет"
        age in 110..120 -> "$age лет"
        lastNum == 1 -> "$age год"
        lastNum in 2..4 -> "$age года"
        else -> "$age лет"
    }
}

/**
 * Простая (2 балла)
 *
 * Путник двигался t1 часов со скоростью v1 км/час, затем t2 часов — со скоростью v2 км/час
 * и t3 часов — со скоростью v3 км/час.
 * Определить, за какое время он одолел первую половину пути?
 */
fun timeForHalfWay(t1: Double, v1: Double, t2: Double, v2: Double, t3: Double, v3: Double): Double {
    val halfDist = ((t1 * v1) + (t2 * v2) + (t3 * v3)) / 2
    val sOne = t1 * v1
    val sTwo = t2 * v2
    val one = halfDist / v1
    val two = t1 + ((halfDist - sOne) / v2)
    val three = t1 + t2 + ((halfDist - sOne - sTwo) / v3)
    return if (halfDist <= (t1 * v1)) one
    else if ((halfDist <= ((t1 * v1) + (t2 * v2))) && (halfDist > (t1 * v1))) two
    else three
}

/**
 * Простая (2 балла)
 *
 * Нa шахматной доске стоят черный король и две белые ладьи (ладья бьет по горизонтали и вертикали).
 * Определить, не находится ли король под боем, а если есть угроза, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от первой ладьи, 2, если только от второй ладьи,
 * и 3, если угроза от обеих ладей.
 * Считать, что ладьи не могут загораживать друг друга
 */
fun whichRookThreatens(
    kingX: Int, kingY: Int,
    rookX1: Int, rookY1: Int,
    rookX2: Int, rookY2: Int
): Int {
    return when {
        ((kingX == rookX1) && (kingX == rookX2)) -> 3
        ((kingY == rookY1) && (kingY == rookY2)) -> 3
        (((kingX == rookX1) && (kingY == rookY2)) || ((kingX == rookX2) && (kingY == rookY1))) -> 3
        ((kingX == rookX1) || (kingY == rookY1)) -> 1
        ((kingX == rookX2) || (kingY == rookY2)) -> 2
        else -> 0
    }
}

/**
 * Простая (2 балла)
 *
 * На шахматной доске стоят черный король и белые ладья и слон
 * (ладья бьет по горизонтали и вертикали, слон — по диагоналям).
 * Проверить, есть ли угроза королю и если есть, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от ладьи, 2, если только от слона,
 * и 3, если угроза есть и от ладьи и от слона.
 * Считать, что ладья и слон не могут загораживать друг друга.
 */
fun rookOrBishopThreatens(
    kingX: Int, kingY: Int,
    rookX: Int, rookY: Int,
    bishopX: Int, bishopY: Int
): Int {
    val diffX = modul(kingX - bishopX)
    val diffY = modul(kingY - bishopY)
    return when {
        ((kingX == rookX) && (diffX == diffY)) -> 3
        ((kingY == rookY) && (diffX == diffY)) -> 3
        /**(((kingX == rookX1) && (kingY == rookY2)) || ((kingX == rookX2) && (kingY == rookY1))) -> 3*/
        ((kingX == rookX) || (kingY == rookY)) -> 1
        (diffX == diffY) -> 2
        else -> 0
    }
}

/**
 * Простая (2 балла)
 *
 * Треугольник задан длинами своих сторон a, b, c.
 * Проверить, является ли данный треугольник остроугольным (вернуть 0),
 * прямоугольным (вернуть 1) или тупоугольным (вернуть 2).
 * Если такой треугольник не существует, вернуть -1.
 */
fun triangleKind(a: Double, b: Double, c: Double): Int {
    val maxNum = max(a, b, c)
    val one = if (a == maxNum) b else if (b == maxNum) a else a
    val two = if (a == maxNum) c else if (b == maxNum) c else b
    return when {
        (((a + b) < c) || ((a + c) < b) || ((b + c) < a)) -> -1
        ((sqr(one) + sqr(two)) < sqr(maxNum)) -> 2
        ((sqr(one) + sqr(two)) == sqr(maxNum)) -> 1
        else -> 0
    }
}

/**
 * Средняя (3 балла)
 *
 * Даны четыре точки на одной прямой: A, B, C и D.
 * Координаты точек a, b, c, d соответственно, b >= a, d >= c.
 * Найти длину пересечения отрезков AB и CD.
 * Если пересечения нет, вернуть -1.
 */
fun segmentLength(a: Int, b: Int, c: Int, d: Int): Int {
    val length = b - c
    return when {
        (((a <= c) && (b >= d))) -> d - c
        ((a >= c) && (b <= d)) -> b - a
        ((a > c) && (b > d) && (d >= a)) -> d - a
        ((a < c) && (b < d) && (b >= c)) -> b - c
        (length == 0) -> 0
        else -> -1
    }
}