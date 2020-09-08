CREATE TABLE account {
  id SERIAL PRIMARY KEY,
  email text NOT NULL UNIQUE,
  password text NOT NULL,
  created_at timestamp with time zone DEFAULT now() NOT NULL,
  updated_at timestamp with time zone DEFAULT now() NOT NULL
};
--;;
CREATE TABLE profile {
  id SERIAL PRIMARY KEY,
  account_id INTEGER NOT NULL UNIQUE REFERENCES account(id),
  display_name text NOT NULL UNIQUE,
  created_at timestamp with time zone DEFAULT now() NOT NULL,
  updated_at timestamp with time zone DEFAULT now() NOT NULL
};
--;;
INSERT INTO account (
  id,
  email,
  password
)
VALUES (
  1,
  "jayzawrotny@gmail.com",
  "bcrypt+sha512$fb657f3492de62da4603c056726e2a51$12$f4f0afb481b32452ae012be847d310da7eda5d9db6ae4df6"
);
--;;
INSERT INTO profile (
  account_id,
  display_name
)
VALUES (
  1,
  "admin",
)
