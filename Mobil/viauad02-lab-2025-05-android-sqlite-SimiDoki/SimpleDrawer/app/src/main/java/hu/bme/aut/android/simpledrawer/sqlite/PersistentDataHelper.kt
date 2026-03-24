package hu.bme.aut.android.simpledrawer.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import hu.bme.aut.android.simpledrawer.model.Line
import hu.bme.aut.android.simpledrawer.model.Point

class PersistentDataHelper(context: Context) {
    private var database: SQLiteDatabase? = null
    private val dbHelper: DbHelper = DbHelper(context)


    private val pointColumns = arrayOf(
        DbConstants.Points.Columns.ID.name,
        DbConstants.Points.Columns.COORD_X.name,
        DbConstants.Points.Columns.COORD_Y.name,
    )

    private val lineColumns = arrayOf(
        DbConstants.Lines.Columns.ID.name,
        DbConstants.Lines.Columns.START_X.name,
        DbConstants.Lines.Columns.START_Y.name,
        DbConstants.Lines.Columns.END_X.name,
        DbConstants.Lines.Columns.END_Y.name,
    )


    @Throws(SQLiteException::class)
    fun open() {
        database = dbHelper.writableDatabase
    }

    fun close() {
        dbHelper.close()
    }

    //PersistPoints
    fun persistPoints(points: List<Point>) {
        clearPoints()
        for (point in points) {
            val values = ContentValues()
            values.put(DbConstants.Points.Columns.COORD_X.name, point.x)
            values.put(DbConstants.Points.Columns.COORD_Y.name, point.y)
            database!!.insert(DbConstants.Points.DATABASE_TABLE, null, values)
        }
    }


    //RestorePoints
    fun restorePoints(): MutableList<Point> {
        val points: MutableList<Point> = ArrayList()
        val cursor = database!!.query(
            DbConstants.Points.DATABASE_TABLE,
            pointColumns,
            null,
            null,
            null,
            null,
            null
        )
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val point: Point = cursorToPoint(cursor)
            points.add(point)
            cursor.moveToNext()
        }
        cursor.close()
        return points
    }


    //ClearPoints
    fun clearPoints() {
        database!!.delete(DbConstants.Points.DATABASE_TABLE, null, null)
    }


    //CursorToPoint
    private fun cursorToPoint(cursor: Cursor): Point {
        val point = Point(
            cursor.getFloat(DbConstants.Points.Columns.COORD_X.ordinal),
            cursor.getFloat(DbConstants.Points.Columns.COORD_Y.ordinal),
            Color(Color.Red.toArgb())
        )
        return point
    }


    //PersistLines
    fun persistLines(lines: List<Line>) {
        clearLines()
        for (line in lines) {
            val values = ContentValues()
            values.put(DbConstants.Lines.Columns.START_X.name, line.start.x)
            values.put(DbConstants.Lines.Columns.START_Y.name, line.start.y)
            values.put(DbConstants.Lines.Columns.END_X.name, line.end.x)
            values.put(DbConstants.Lines.Columns.END_Y.name, line.end.y)
            database!!.insert(DbConstants.Lines.DATABASE_TABLE, null, values)
        }
    }


    //RestoreLines
    fun restoreLines(): MutableList<Line> {
        val lines: MutableList<Line> = ArrayList()
        val cursor = database!!.query(
            DbConstants.Lines.DATABASE_TABLE,
            lineColumns,
            null,
            null,
            null,
            null,
            null
        )
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val line: Line = cursorToLine(cursor)
            lines.add(line)
            cursor.moveToNext()
        }
        cursor.close()
        return lines
    }


    //ClearLines
    fun clearLines() {
        database!!.delete(DbConstants.Lines.DATABASE_TABLE, null, null)
    }

    //CursorToLine
    private fun cursorToLine(cursor: Cursor): Line {
        val line = Line(
            Point(
                cursor.getFloat(DbConstants.Lines.Columns.START_X.ordinal),
                cursor.getFloat(DbConstants.Lines.Columns.START_Y.ordinal)
            ),
            Point(
                cursor.getFloat(DbConstants.Lines.Columns.END_X.ordinal),
                cursor.getFloat(DbConstants.Lines.Columns.END_Y.ordinal)
            ),
            Color(Color.Red.toArgb())
        )
        return line
    }

}
