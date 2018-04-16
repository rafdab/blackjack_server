#ifndef STRUCTS_H
#define STRUCTS_H

typedef struct player{
	int score;
	char p_name[];
}Player;

typedef struct node{
	struct sockaddr address;
	struct node *next;
}Client_node;

#endif