create table map_segment(
	id int auto_increment primary key,
    name  varchar(50),
    type varchar(16),
    file_path varchar(50),
    xpos int,
    ypos int,
    width int,
    height int
);