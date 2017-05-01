<?php

/**
 * Created by PhpStorm.
 * User: kassem
 * Date: 30/4/17
 * Time: 10:07 PM
 */
class report
{

    /**
     * @url GET /sales
     */
    public function getSalesReport($from,$to)
    {
        $con = mysqli_connect('localhost', 'root', '', 'supermarket');
        $con->set_charset("utf8");
        $result = mysqli_query($con, "select * from report_sales where new_date>= '$from' AND new_date <= '$to' order by new_date DESC ");
        $items = array();
        while ($row = mysqli_fetch_array($result)) {
            $item = array(
                'new_date' => $row['new_date'],
                'sales' => $row['sales']);
            $items[] = $item;
        }
        mysqli_close($con);
        return $items;
    }

    /**
     * @url GET /best_selling
     */
    public function getBestSellingReport()
    {
        $con = mysqli_connect('localhost', 'root', '', 'supermarket');
        $con->set_charset("utf8");
        $result = mysqli_query($con, "select * from report_best_selling");
        $items = array();
        while ($row = mysqli_fetch_array($result)) {
            $item = array(
                'product' => $row['product'],
                'sales' => $row['sales']);
            $items[] = $item;
        }
        mysqli_close($con);
        return $items;
    }
}