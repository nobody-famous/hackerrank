CC 			:= gcc

TARGET 		:= solution

SRCDIR 		:= src
BUILDDIR	:= obj
TARGETDIR	:= bin
SRCEXT		:= c
OBJEXT		:= o

CFLAGS		:= -Wall -g

SOURCES		:= $(shell find $(SRCDIR) -type f -name *.$(SRCEXT))
OBJECTS		:= $(patsubst $(SRCDIR)/%, $(BUILDDIR)/%, $(SOURCES:.$(SRCEXT)=.$(OBJEXT)))

all: directories $(TARGET)

run_00: all
	$(TARGETDIR)/$(TARGET) < input/input00.txt

run_02: all
	$(TARGETDIR)/$(TARGET) < input/input02.txt

run_03: all
	$(TARGETDIR)/$(TARGET) < input/input03.txt

run_07: all
	$(TARGETDIR)/$(TARGET) < input/input07.txt

run_wiki: all
	$(TARGETDIR)/$(TARGET) < input/wiki.txt

mem_02: all
	valgrind --leak-check=yes $(TARGETDIR)/$(TARGET) < input/input02.txt

time_02: all
	time $(TARGETDIR)/$(TARGET) < input/input02.txt

time_03: all
	time $(TARGETDIR)/$(TARGET) < input/input03.txt

debug_02: all
	gdb $(TARGETDIR)/$(TARGET) < input/input02.txt

rebuild: clean all

directories:
	@mkdir -p $(TARGETDIR)
	@mkdir -p $(BUILDDIR)

clean:
	@$(RM) -rf $(TARGETDIR)
	@$(RM) -rf $(BUILDDIR)

$(TARGET): $(OBJECTS)
	$(CC) -o $(TARGETDIR)/$(TARGET) $^

$(BUILDDIR)/%.$(OBJEXT): $(SRCDIR)/%.$(SRCEXT)
	@mkdir -p $(dir $@)
	$(CC) $(CFLAGS) -c -o $@ $<

.PHONY: all clean run
