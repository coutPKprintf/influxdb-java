package org.influxdb;

import org.influxdb.dto.Point;

import java.util.List;

/**
 * Created by cw on 16-1-9.
 */
public interface InfluxDBProxy {
    /**
     * Random write a single Point to the database.
     *
     * @param point
     *            The point to write
     */
     void writeWithRandom(final Point point);

    /**
     *write a single Point to the database.
     * @param point
     * @param host
     */
     void writeWithHost(final Point point,final String host);

    /**
     * write a single Point to the database by LRU algorithm
     * @param point
     */
     void writeWithLRU(final Point point);


    /**
     * Random write pointList to the database.
     *
     * @param pointList
     *            The point to write
     */
    void writeWithRandom(final List<Point> pointList);

    /**
     * Write pointList to the database.
     * @param pointList
     * @param host
     */
    void writeWithHost(final List<Point> pointList,final String host);

    /**
     * Write pointList to the database by LRU algorithm
     * @param pointList
     */
    void writeWithLRU(final List<Point> pointList);
}
