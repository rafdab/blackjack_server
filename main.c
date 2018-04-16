#include <stdio.h>
#include <string.h>
#include <netinet/in.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <netdb.h>

#include "structs.h"

int main(int argc, char const *argv[])
{
	if (argc < 2){
		printf("Please enter port number as a parameter\n");
		return 1;
	}

	int s_port = (int)argv[1];
	struct sockaddr_in s_adr, *c_adr;
	int s_socket, c_socket;
	char *ip;

	s_socket = socket(PF_INET, SOCK_STREAM, 0);
	s_adr.sin_family = AF_INET;
	s_adr.sin_port = htons(s_port);
	s_adr.sin_addr.s_addr = INADDR_ANY;
	if(bind(s_socket, (struct sockaddr*)&s_adr, sizeof(s_adr)) < 0){
		printf("Unsucessfull bind()\n");
		return 1;
	}

	ip = inet_ntoa(s_adr.sin_addr);
	printf("Server started\nPlease connect your client application to %s %s\n", ip, argv[1]);

	close(s_socket);
	return 0;
}