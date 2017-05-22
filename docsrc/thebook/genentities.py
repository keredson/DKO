import sys
for i in range(int(sys.argv[1]), int(sys.argv[2])):
  print('  <!ENTITY test%ijva SYSTEM "../../gen/docbook/test%i.jva">' % (i,i))
  print('  <!ENTITY test%iout SYSTEM "../../gen/docbook/test%i.out">' % (i,i))
