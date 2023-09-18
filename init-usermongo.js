db.createUser(
    {
        user: "tomwodz",
        pwd: "tomwodz",
        roles: [
            {
                role: "readWrite",
                db: "mongo_joboffers"
            }
        ]
    }
)