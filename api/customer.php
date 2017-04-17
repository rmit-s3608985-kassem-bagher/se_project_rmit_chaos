<?php

/**
 * Created by PhpStorm.
 * User: kassem
 * Date: 17/4/17
 * Time: 7:30 PM
 */
class customer
{
    public function login($username, $password)
    {
        $con = mysqli_connect('localhost', 'root', '', 'supermarket');
        $con->set_charset("utf8");
        $result = mysqli_query($con, "select * from customer where cust_username ='$username' and cust_password='$password'");
        $row = $result->fetch_assoc();
        if ($row == null)
            throw new RestException(401, 'username and password not found');

        $customer = new stdClass();
        $customer->id = $row['cust_id'];
        $customer->name = $row['cust_name'];
        $customer->balance = $row['cust_balance'];
        $customer->points = $row['cust_points'];
        return $customer;
    }
}