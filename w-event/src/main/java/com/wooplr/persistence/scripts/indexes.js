db.event_count.createIndex({time_interval_in_hours:1, count:-1}, {background:true});
db.event_log.ensureIndex({type:1,time_interval_in_hours:1}, {background:true});
db.event_log.ensureIndex({expiry_date:1,expireAfterSeconds:0}, {background:true});