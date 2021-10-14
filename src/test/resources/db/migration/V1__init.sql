CREATE TABLE IF NOT EXISTS processing_tasks(
  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  progress_status VARCHAR,
  start_time TIMESTAMP WITH TIME ZONE,
  end_time TIMESTAMP WITH TIME ZONE,
  tmp_file_path VARCHAR
);


CREATE TABLE IF NOT EXISTS node_correlations_statistics(
   id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
   task_id BIGINT REFERENCES processing_tasks(id),
   type VARCHAR,
   count BIGINT,
   avg DECIMAL,
   min BIGINT,
   max BIGINT
);