<?php

require_once '../restler.php';

$r = new Restler();
$r->setSupportedFormats('JsonFormat');
$r->addAPIClass('employee');
$r->handle();